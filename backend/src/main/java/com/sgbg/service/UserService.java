package com.sgbg.service;

import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.domain.*;
import com.sgbg.repository.ParticipationRepository;
import com.sgbg.repository.RoomRepository;
import com.sgbg.repository.UserRepository;
import com.sgbg.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    @Transactional
    public User createUser(Map<String, String> userInfo) {
        User user = User.builder()
                .name(userInfo.get("name"))
                .email(userInfo.get("email"))
                .memberScore(50)
                .hostScore(50)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findUserById(userId).orElse(null);
    }

    @Override
    @Transactional
    public Participation addMyRoom(Long userId, Long roomId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            throw new NotFoundException("Room Not Found");
        }

        Participation participation = participationRepository.findParticipationByUserAndRoom(user, room).orElse(null);
        if (participation == null) {
            participation = Participation.builder()
                    .user(user)
                    .room(room)
                    .build();
        }

        participation.addMember(user, room);
        return participationRepository.save(participation);
    }

    @Override
    public List<Room> getMyRooms(Long userId, Boolean isHost) {
        User user = getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        List<Room> myRoomsByUserId = participationRepository.findMyRoomsByUserId(userId);
        List<Room> getRooms = new ArrayList<>();

        if (isHost) { // host = true
            return myRoomsByUserId
                    .stream().filter(room -> room.getHostId().equals(userId))
                    .collect(Collectors.toList());
        }
        return myRoomsByUserId
                .stream().filter(room -> !room.getHostId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Room deleteMyRoom(Long userId, Long roomId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            throw new NotFoundException("Room Not Found");
        }
        Participation participation = participationRepository.findParticipationByUserAndRoom(user, room).orElse(null);
        if (participation != null) {
            participation.deleteMember(user, room);
            participationRepository.delete(participation);
        }

        return room;
    }

    public void updateHostScore(Long hostId) {
        User host = userRepository.findUserById(hostId).orElse(null);
        if (host == null) {
            throw new NotFoundException("User Not Found");
        }

        List<Room> myRooms = getMyRooms(hostId, true);
        long totalMembers = 0;
        long failReviews = 0;

        for (Room room : myRooms) {
            totalMembers += room.getMembers().size();

            List<HostEvaluation> hostEvaluations = room.getHostEvaluations();
            for (HostEvaluation hostEvaluation : hostEvaluations) {
                if (!hostEvaluation.getIsSuccess()) {
                    failReviews++;
                }
            }
        }

        host.setHostScore((int) (failReviews / totalMembers * 100));
        userRepository.save(host);
    }

    public void updateMemberScore(Long userId, int score) {
        User user = userRepository.findUserById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        // user score = (나를 평가한 점수들의 합) / (나를 평가한 사람 수 * 10)
        List<Room> myRooms = participationRepository.findMyRoomsByUserId(userId);
        long totalScore = 0;
        long totalReviewers = 0;

        for (Room room : myRooms) {
            List<MemberEvaluation> memberEvaluations = room.getMemberEvaluations();

            for (MemberEvaluation memberEvaluation : memberEvaluations) {
                if (memberEvaluation.getUser().getId() == userId) {
                    totalScore += memberEvaluation.getScore();
                    totalReviewers++;
                }
            }
        }

        user.setMemberScore((int) (totalScore / (totalReviewers * 10))*100);
    }
}

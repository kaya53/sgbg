package com.sgbg.service;

import com.sgbg.api.request.RoomReq;
import com.sgbg.blockchain.common.exception.NoWalletException;
import com.sgbg.blockchain.common.exception.NotEnoughMoneyException;
import com.sgbg.blockchain.domain.Wallet;
import com.sgbg.blockchain.repository.WalletRepository;
import com.sgbg.blockchain.service.SingleBungleService;
import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.domain.Location;
import com.sgbg.domain.Participation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.LocationRepository;
import com.sgbg.repository.ParticipationRepository;
import com.sgbg.repository.RoomRepository;
import com.sgbg.repository.UserRepository;
import com.sgbg.service.interfaces.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    @Transactional
    public Room createRoom(RoomReq roomReq, Long userId) throws NoWalletException, NotEnoughMoneyException {

        Wallet wallet = walletRepository.findByOwnerId(userId).orElse(null);
        if(wallet == null){
            throw new NoWalletException();
        }
        if(wallet.getCash() < roomReq.getPrice()){
            throw new NotEnoughMoneyException();
        }
        // Room 생성
        Room room = roomReq.toEntity(roomReq);
        Location location = roomReq.getLocation();

        User user = userRepository.findUserById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }
        room.setHostId(user.getId());
        room.setHostName(user.getName());

//        room.builder().hostId(user.getUserId);
//        room.builder().hostId(user.getUserId())
//                        .hostName(user.getName()).build();

        // User - Room 관계 엔티티 생성
        Participation participation = Participation.builder()
                .user(user)
                .room(room)
                .build();
        participation.addMember(user, room);

        participationRepository.save(participation);
        locationRepository.save(location);
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room setRoomContactAddress(Room room, String contactAddress) {
        room.setContractAddress(contactAddress);
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getRoomList() {
        return roomRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            throw NullPointerException("not find room");
//        }
    }

    @Override
    public List<Room> getParentRoomList(String parentCategory) {
//        List<RoomRes> roomResList = new ArrayList<>();
//        for(Room room : roomList){
//            if(room.getParentCategory().equals(parentCategory)){
//                roomResList.add(RoomRes.getRoomRes(room));
//            }
//        }
        return roomRepository.findAllByParentCategory(parentCategory);
    }

    @Override
    public List<Room> getChildRoomList(String childCategory) {
//        List<RoomRes> roomResList = new ArrayList<>();
//        for(Room room : roomList){
//            if(room.getChildCategory().equals(childCategory)){
//                roomResList.add(RoomRes.getRoomRes(room));
//            }
//        }
        return roomRepository.findAllByChildCategory(childCategory);
    }

    @Override
    public List<Room> searchRoom(String keyword) {
        List<Room> rooms = roomRepository.findByTitleContaining(keyword);
        return rooms;
    }
}

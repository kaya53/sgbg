// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0;

import "@openzeppelin/contracts/utils/Strings.sol";
import "./IERC20.sol";

interface SingleBungleInterface {
    function enterRoom(address _member, uint _value) external payable; // 입장하기
    function leaveRoom(address _member, uint _value) external payable; // 퇴장하기
    function withdraw(address _sender) external payable; // 방장이 출금하기
    function isSuccess(address _member, bool _flag) external; // 성공/실패 여부 저장
}

contract SingleBungle is SingleBungleInterface {
    IERC20 public sgbgToken; // SBTKN

    address private host; // 방장
    uint private memberLength; // 참여자 수

    uint private MINIMUM_AMOUNT; // 최소 모금액 결정 (0.01 ETH == 1e16)
    uint private recruitEndDate; // 모집 마감 기간
    uint private surveyStartDate; // 성공/실패 여부 설문 시작 날짜
    uint private surveyEndDate; // 성공/실패 여부 설문 마감 기간

    // key-value 쌍의 hash table
    mapping(address => uint) memberToMoney; // member address - money
    mapping(address => bool) memberToSuccess; // member address - isSuccess

    constructor(address _token, address _host, uint _duration, uint _minimumAmount) {
        sgbgToken = IERC20(_token);

        host = _host; // 방장 설정
        recruitEndDate = block.timestamp + _duration; // 모금 마감 기간
        MINIMUM_AMOUNT = _minimumAmount; // 1인당 최소 모금액 설정
    }

    function _transferFrom(address _sender, address _receiver, uint _amount) private {
        bool sent = sgbgToken.transferFrom(_sender, _receiver, _amount);
        require(sent, "TRANSFER FAILED");
    }

    // 지불 가능한 참가자인가
    modifier onlyPayableMember() {
        require(msg.value >= MINIMUM_AMOUNT, string.concat(string.concat("MINIMUM AMOUNT: ", Strings.toString(MINIMUM_AMOUNT)), " ETH"));
        _;
    }

    // 모금 기한 내의 모금인가
    modifier onlyWithinRecruitPeriod() {
        require(block.timestamp < recruitEndDate, "ROOM CLOSED");
        _;
    }

    // 입장하기: 참가자 추가 + 참가자 ETH 지불
    function enterRoom(address _member, uint _value) external payable
    onlyPayableMember
    onlyWithinRecruitPeriod {
        memberLength++; // add member
        memberToMoney[_member] = _value; // save who send wei
        memberToSuccess[_member] = true;
        sgbgToken.transferFrom(_member, address(this), _value); // send SBTKN from member to contract
        // payable(address(this)).transfer(msg.value); // send wei from member to contract
    }

    // 해당 방 유저인 경우
    modifier onlyRoomMember(address _member) {
        require(memberToMoney[_member] > 0, "NOT A ROOM MEMBER");
        _;
    }

    // 퇴장하기: 참가자 삭제 + 참가자가 송금했던 ETH 출금
    function leaveRoom(address _member, uint _value) external payable
    onlyRoomMember(_member)
    onlyWithinRecruitPeriod {
        memberLength--; // delete member
        sgbgToken.transferFrom(address(this), _member, memberToMoney[_member]); // send SBTKN from contract to member
        // payable(msg.sender).transfer(memberToMoney[msg.sender]); // send eth from contract to member
        delete memberToMoney[_member]; // delete who send eth
    }

    // 방장만 ETH 출금 가능
    modifier onlyHost(address _sender) {
        require(_sender == host, "NOT HOST ADDRESS");
        _;
    }

    // 모집 마감되었을 경우
    modifier onlyAfterRoomCloses() {
        require(block.timestamp > recruitEndDate, "ROOM NOT CLOSED YET");
        _;
    }

    // 출금하기: 모집이 성공적으로 마감되었을 때, ETH 출금
    function withdraw(address _sender) public payable
    onlyHost(_sender)
    onlyAfterRoomCloses {
        sgbgToken.transferFrom(address(this), _sender, address(this).balance); // send all SBTKN from contract to member
        // payable(msg.sender).transfer(address(this).balance); // send all eth from contract to member
    }

    // 현재 contract가 보유하고 있는 ETH 반환
    function getBalance() public view returns(uint256) {
        if(address(this) == address(0)) return 0;
        return address(this).balance;
    }

    modifier onlyWithinSurveyPeriod() {
        require((surveyStartDate <= block.timestamp) && (block.timestamp < surveyEndDate),"NOT A SURVEY PERIOD");
        _;
    }

    // 성공/실패 여부 저장: 실패 시, 성공 값 변경
    function isSuccess(address _member, bool _flag) external
    onlyWithinSurveyPeriod {
        if(_flag == false) {
            memberToSuccess[_member] = _flag;
        }
    }
}
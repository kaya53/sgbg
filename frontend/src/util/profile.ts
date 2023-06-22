export const getProgressColor = (score: number) => {
  const color = [
    "",
    "bg-red-100",
    "bg-blue-300",
    "bg-green-100",
    "bg-light-green-200",
    "bg-yellow-200",
  ];
  if (score < 20) {
    return [color[1]];
  } else if (score < 40) {
    return [color[2]];
  } else if (score < 60) {
    return [color[3]];
  } else if (score < 80) {
    return [color[4]];
  } else {
    return [color[5]];
  }
}

export const getParticipantNickname = (score: number) => {
  const participant = [
    "",
    "눈물나는 참여자",
    "섭섭한 참여자",
    "괜찮은 참여자",
    "성실한 참여자",
    "열정적인 참여자",
  ];
  if (score < 20) {
    return [participant[1]];
  } else if (score < 40) {
    return [participant[2]];
  } else if (score < 60) {
    return [participant[3]];
  } else if (score < 80) {
    return [participant[4]];
  } else {
    return [participant[5]];
  }
};
export const getHostNickname = (score: number) => {
  const host = [
    "",
    "위험한 방장",
    "부족한 방장",
    "아쉬운 방장", 
    "준수한 방장",
    "타고난 방장"
  ];
    
  if (score < 20) {
    return [host[1]];
  } else if (score < 40) {
    return [host[2]];
  } else if (score < 60) {
    return [host[3]];
  } else if (score < 80) {
    return [host[4]];
  } else {
    return [host[5]];
  }
};

export const getHostBadge = (score: number) => {
  const host = [
    "",
    "💔",
    "🥉",
    "🥈", 
    "🥇",
    "🏆"
  ];
    
  if (score < 20) {
    return [host[1]];
  } else if (score < 40) {
    return [host[2]];
  } else if (score < 60) {
    return [host[3]];
  } else if (score < 80) {
    return [host[4]];
  } else {
    return [host[5]];
  }
}

export const getParticipantBadge = (score: number) => {
  if (score < 20) {
    return "1";
  } else if (score < 40) {
    return "2";
  } else if (score < 60) {
    return "3";
  } else if (score < 80) {
    return "4";
  } else {
    return "5";
  }
};

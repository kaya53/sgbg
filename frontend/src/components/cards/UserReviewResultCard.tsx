
const UserReviewResultCard = (props:any) => {
    return (
        <div className="flex justify-start items-center">
            <div className="bg-gray-300 rounded-lg p-1 font-light text-xs mr-1">
                <span className="mr-1">😍</span>
                <span className="mr-1">최고에요!</span>
                <span className="ml-1 font-bold">{ props.result.best }</span>
            </div>
            <div className="bg-gray-300 rounded-lg p-1 font-light text-xs mr-1">
                <span className="mr-1">😁</span>
                <span className="mr-1">좋아요!</span>
                <span className="ml-1 font-bold">{ props.result.good }</span>
            </div>
            <div className="bg-gray-300 rounded-lg p-1 font-light text-xs">
                <span className="mr-1">😑</span>
                <span className="mr-1">별로에요</span>
                <span className="ml-1 font-bold">{ props.result.bad }</span>
            </div>
        </div>
    );
};

export default UserReviewResultCard;
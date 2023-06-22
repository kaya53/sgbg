
const FilterBar = (props:any) => {
    return (
        <div className="w-full">
            <div className="w-per90 flex flex-row justify-start items-center py-2">
                <div className="border border-gray-300 rounded-full font-light text-sm py-1 px-2 ml-2" onClick={e=> props.setSelected('whole')}>전체</div>
                <div className="border border-gray-300 rounded-full font-light text-sm py-1 px-2 ml-2" onClick={e=> props.setSelected('ongoing')}>모집중</div>
                <div className="border border-gray-300 rounded-full font-light text-sm py-1 px-2 ml-2" onClick={e=> props.setSelected('imminent')}>마감임박</div>
                <div className="border border-gray-300 rounded-full font-light text-sm py-1 px-2 ml-2" onClick={e=> props.setSelected('done')}>모집완료</div>
            </div>
        </div>
    );
};

export default FilterBar;
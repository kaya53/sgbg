type SearchMapCardProps = {
  id: string;
  place_name: string;
  road_address_name: string;
  category_name: string;
  phone: string;
  x: string;
  y: string;
  selectMapCard: (id: string, name: string, x: string, y: string, road_address:string) => void;
};
const SearchMapCard = ({
  selectMapCard,
  id,
  place_name,
  road_address_name,
  category_name,
  phone,
  x,
  y,
}: SearchMapCardProps) => {
  const handleClick = () => {
    selectMapCard(id, place_name, x, y, road_address_name);
  };
  return (
    <div className="flex flex-col rounded border border-gray-200 p-2 mb-2" onClick={handleClick}>
      <span>
        <a className="font-semibold underline" href={`https://place.map.kakao.com/m/${id}`}>
          {place_name}
        </a>
      </span>
      <span className="font-light text-sm">{category_name}</span>
      <span className="font-light text-sm">{phone}</span>
      <span>{road_address_name}</span>
    </div>
  );
};

export default SearchMapCard;

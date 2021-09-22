import ItemCardContainer from "./ItemCardContainer";

export default function ItemGallery({
  map,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {
    if( !Object.keys(map).length === null){
    return map.forEach((date, valueList) => (
      <ItemCardContainer
        key={date}
        valueList={valueList}
        valueTitle={valueTitle}
        reloadList={reloadList}
        editService={editService}
        removeService={removeService}
      />))}
    else {
        return null
    }



}

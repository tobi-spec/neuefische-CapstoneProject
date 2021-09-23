import ItemCardContainer from "./ItemCardContainer";

export default function ItemGallery({
  itemMaps,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {


  if( valueTitle === "Food"){
    return itemMaps.map(item => (
        <ItemCardContainer
            date={item.date}
            valueList={item.foods}
            valueTitle={valueTitle}
            reloadList={reloadList}
            editService={editService}
            removeService={removeService}
            key={item.date}
        />))
  }

  if( valueTitle === "Pain"){
    return itemMaps.map(item => (
        <ItemCardContainer
            date={item.date}
            valueList={item.pains}
            valueTitle={valueTitle}
            reloadList={reloadList}
            editService={editService}
            removeService={removeService}
            key={item.date}
        />))
  }

}


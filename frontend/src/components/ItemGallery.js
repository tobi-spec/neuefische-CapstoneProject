import ItemCard from './ItemCard'

export default function ItemGallery({
  List,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {
  if (valueTitle === 'Food') {
    return List.map(Item => (
      <ItemCard
        valueTitle={valueTitle}
        mainValue={Item.foodName}
        date={Item.date}
        id={Item.id}
        key={Item.id}
        reloadList={reloadList}
        editService={editService}
        removeService={removeService}
      />
    ))
  }
  if (valueTitle === 'Pain') {
    return List.map(Item => (
      <ItemCard
        valueTitle={valueTitle}
        mainValue={Item.painLevel}
        date={Item.date}
        id={Item.id}
        key={Item.id}
        reloadList={reloadList}
        editService={editService}
        removeService={removeService}
      />
    ))
  }
}

import ItemCardContainer from "./ItemCardContainer";
import styled from "styled-components";

export default function ItemGallery({
  itemMaps,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {

  const Container = itemMaps.map(item => (
      <ItemCardContainer
          key = {item.date}
          date={item.date}
          valueList={item.foods}
          valueTitle={valueTitle}
          reloadList={reloadList}
          editService={editService}
          removeService={removeService}
      />))

  return(
    <Wrapper>
      {Container}
    </Wrapper>
  )
}

const Wrapper = styled.div``

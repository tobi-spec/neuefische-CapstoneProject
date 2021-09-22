import ItemCardContainer from "./ItemCardContainer";
import styled from "styled-components";

export default function ItemGallery({
  map,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {
  const Container = map.forEach((date, valueList) => (
      <ItemCardContainer
          key={date}
          valueList={valueList}
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

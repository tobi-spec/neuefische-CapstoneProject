import ItemCardContainer from "./ItemCardContainer";
import styled from "styled-components";

export default function ItemGallery({
  itemMaps,
  valueTitle,
  reloadList,
  editService,
  removeService,
}) {

  const foodContainer = itemMaps.map(item => (
      <ItemCardContainer
          date={item.date}
          valueList={item.foods}
          valueTitle={valueTitle}
          reloadList={reloadList}
          editService={editService}
          removeService={removeService}
          key={item.date}
      />))

  const painContainer = itemMaps.map(item => (
      <ItemCardContainer
          date={item.date}
          valueList={item.pains}
          valueTitle={valueTitle}
          reloadList={reloadList}
          editService={editService}
          removeService={removeService}
          key={item.date}
      />))

  if( valueTitle === "Food"){
    return <Wrapper>
      {foodContainer}
          </Wrapper>
  }

  if( valueTitle === "Pain"){
    return <Wrapper>
      {painContainer}
          </Wrapper>
  }

}

const Wrapper = styled.div``



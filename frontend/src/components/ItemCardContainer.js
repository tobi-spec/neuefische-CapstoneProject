import ItemCard from "./ItemCard";
import styled from "styled-components";

export default function ItemCardContainer({
  date,
  valueList,
  valueTitle,
  reloadList,
  editService,
  removeService}){

    const cards = valueList.map(value => (
        <ItemCard
            mainValue= {value.foodName}
            id = {value.id}
            valueTitle={valueTitle}
            reloadList={reloadList}
            editService={editService}
            removeService={removeService}
        />
    ))

    if( valueTitle === "Food"){
        return <Wrapper>
                <h1> Day: {date}</h1>
                {cards}
            </Wrapper>
    }
    if( valueTitle === "Pain"){
        return valueList.map (Item =>
            <Wrapper>
                <h1> Day: {date}</h1>
                <ItemCard
                    mainValue = {Item.painLevel}
                    id = {Item.id}
                    valueTitle={valueTitle}
                    reloadList={reloadList}
                    editService={editService}
                    removeService={removeService}
                />
            </Wrapper>
        )}
}

const Wrapper = styled.div``
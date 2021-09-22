import ItemCard from "./ItemCard";
import styled from "styled-components";

export default function ItemCardContainer({
  key,
  valueList,
  valueTitle,
  reloadList,
  editService,
  removeService}){

    if( valueTitle === "Food"){
        return valueList.map (Item =>
            <Wrapper>
                <h1> Day: {key}</h1>
                <ItemCard
                    mainValue= {Item.foodName}
                    id = {Item.id}
                    valueTitle={valueTitle}
                    reloadList={reloadList}
                    editService={editService}
                    removeService={removeService}
                />
            </Wrapper>
    )}
    if( valueTitle === "Pain"){
        return valueList.map (Item =>
            <Wrapper>
                <h1> Day: {key}</h1>
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
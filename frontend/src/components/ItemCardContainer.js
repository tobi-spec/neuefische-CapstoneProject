import ItemCard from "./ItemCard";
import styled from "styled-components";

export default function ItemCardContainer({
  date,
  valueList,
  valueTitle,
  reloadList,
  editService,
  removeService}){

    const foodCards = valueList.map(value => (
        <ItemCard
            mainValue= {value.foodName}
            id = {value.id}
            reloadList={reloadList}
            editService={editService}
            removeService={removeService}
            key={value.id}
        />
    ))

    const painCards = valueList.map(value => (
        <ItemCard
            mainValue= {value.painLevel}
            id = {value.id}
            reloadList={reloadList}
            editService={editService}
            removeService={removeService}
            key={value.id}
        />
    ))

    if( valueTitle === "Food"){
        return <Wrapper>
                <h1> Day: {date}</h1>
                <div className="cards">
                {foodCards}
                </div>
            </Wrapper>
    }
    if( valueTitle === "Pain"){
            return <Wrapper>
                <h1> Day: {date}</h1>
                <div className="cards">
                {painCards}
                </div>
            </Wrapper>
        }
}

const Wrapper = styled.div`

.cards {
  display: flex;
  flex-wrap: wrap;
}`
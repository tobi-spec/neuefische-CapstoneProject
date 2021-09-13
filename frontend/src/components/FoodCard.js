import styled from 'styled-components'

export default function FoodCard({ foodName, date }) {
  return (
    <Wrapper>
      <li>
        <p>food: {foodName}</p>
        <p>date: {date}</p>
      </li>
    </Wrapper>
  )
}

const Wrapper = styled.section`
  border-style: solid;
  border-radius: 10px;
  margin: 5px;
  padding: 3px;
`

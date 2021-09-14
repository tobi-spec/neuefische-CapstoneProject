import styled from 'styled-components'

export default function PainCard({ painLevel, date }) {
  return (
    <Wrapper>
      <li>
        <p>pain: {painLevel}</p>
        <p>date: {date}</p>
      </li>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  border-style: solid;
  border-radius: 10px;
  margin: 5px;
  padding: 3px;
`

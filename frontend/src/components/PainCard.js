import styled from 'styled-components'

export default function PainCard({ painLevel, date }) {
  return (
    <Wrapper>
      <li>
        pain: {painLevel}
        date: {date}
      </li>
    </Wrapper>
  )
}

const Wrapper = styled.div``

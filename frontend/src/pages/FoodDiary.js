import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'

export default function FoodDiary() {
  return (
    <Wrapper>
      <Header title="Food Diary" />
      <Content>
        <ul>
          <li>Food1</li>
          <li>Food2</li>
          <li>Food3</li>
        </ul>
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
  ul {
    grid-column: 2;
    grid-row: 2;
  }
`

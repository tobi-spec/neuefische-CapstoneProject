import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useEffect, useState } from 'react'
import { getFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import ItemCard from '../components/ItemCard'

export default function FoodDiary() {
  const { token } = useAuth()
  const [foodList, setFoodList] = useState([])

  useEffect(() => {
    getFood(token).then(data => setFoodList(data.foodList))
  }, [token])

  const reloadFoodList = token => {
    getFood(token).then(data => setFoodList(data.foodList))
  }

  const Items = foodList.map(Item => (
    <ItemCard
      valueTitle={'Food'}
      mainValue={Item.foodName}
      date={Item.date}
      id={Item.id}
      key={Item.id}
      reloadList={reloadFoodList}
    />
  ))

  return (
    <Wrapper>
      <Header title="Food Diary" />
      <Content>
        <ul>{Items}</ul>
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
  ul {
    list-style: none;
    grid-column: 2;
    grid-row: 2;
  }
`

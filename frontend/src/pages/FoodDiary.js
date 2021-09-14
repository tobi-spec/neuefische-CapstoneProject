import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useEffect, useState } from 'react'
import { getFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import FoodCard from '../components/FoodCard'

export default function FoodDiary() {
  const { token } = useAuth()
  const [foodList, setFoodList] = useState([])

  useEffect(() => {
    getFood(token)
      .then(response => response.data)
      .then(data => setFoodList(data.foodList))
  }, [])

  const foodItems = foodList.map(foodItem => (
    <FoodCard
      foodName={foodItem.foodName}
      date={foodItem.date}
      key={foodItem.id}
    />
  ))

  return (
    <Wrapper>
      <Header title="Food Diary" />
      <Content>
        <ul>{foodItems}</ul>
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

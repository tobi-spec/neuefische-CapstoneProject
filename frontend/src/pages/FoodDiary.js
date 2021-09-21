import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useEffect, useState } from 'react'
import { getFood, removeFood, updateFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import ItemGallery from '../components/ItemGallery'

export default function FoodDiary() {
  const { token } = useAuth()
  const [foodMap, setFoodMap] = useState([])

  useEffect(() => {
    getFood(token).then(data => setFoodMap(data.foodMap))
  }, [token])

  const reloadFoodList = token => {
    getFood(token).then(data => setFoodMap(data.foodMap))
  }

  return (
    <Wrapper>
      <Header title="Food Diary" />
      <Content>
        <ul>
          {
            <ItemGallery
              Map={foodMap}
              valueTitle={'Food'}
              reloadList={reloadFoodList}
              editService={updateFood}
              removeService={removeFood}
            />
          }
        </ul>
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

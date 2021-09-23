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
  const [foodMaps, setFoodMaps] = useState([])

  useEffect(() => {
    getFood(token)
        .then(data => setFoodMaps(data.foodMaps))
  }, [token])

  const reloadFoodList = token => {
    getFood(token)
        .then(data => setFoodMaps(data.foodMaps))
  }

  return (
    <Wrapper>
      <Header title="Food Diary" />
      <Content>
          {
            <ItemGallery
              itemMaps={foodMaps}
              valueTitle={'Food'}
              reloadList={reloadFoodList}
              editService={updateFood}
              removeService={removeFood}
            />
          }
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
`

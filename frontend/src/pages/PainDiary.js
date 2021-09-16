import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useAuth } from '../auth/AuthProvider'
import { useEffect, useState } from 'react'
import { getPain } from '../service/AxiosService'
import ItemCard from '../components/ItemCard'

export default function PainDiary() {
  const { token } = useAuth()
  const [painList, setPainList] = useState([])

  useEffect(() => {
    getPain(token)
      .then(response => response.data)
      .then(data => setPainList(data.painList))
  }, [token])

  const Items = painList.map(Item => (
    <ItemCard
      valueTitle={'Pain'}
      mainValue={Item.painLevel}
      date={Item.date}
      id={Item.id}
      key={Item.id}
    />
  ))

  return (
    <Wrapper>
      <Header title="Pain Diary" />
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

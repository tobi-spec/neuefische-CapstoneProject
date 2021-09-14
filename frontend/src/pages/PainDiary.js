import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useAuth } from '../auth/AuthProvider'
import { useEffect, useState } from 'react'
import { getPain } from '../service/AxiosService'
import PainCard from '../components/PainCard'

export default function PainDiary() {
  const { token } = useAuth()
  const [painList, setPainList] = useState([])

  useEffect(() => {
    getPain(token)
      .then(response => response.data)
      .then(data => setPainList(data.painList))
  }, [token])

  const painItems = painList.map(painItem => (
    <PainCard
      painLevel={painItem.painLevel}
      date={painItem.date}
      key={painItem.id}
    />
  ))

  return (
    <Wrapper>
      <Header title="Pain Diary" />
      <Content>
        <ul>{painItems}</ul>
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

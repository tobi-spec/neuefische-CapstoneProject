import styled from 'styled-components'
import Header from '../components/Header'
import Content from '../components/Content'
import Footer from '../components/Footer'
import { useAuth } from '../auth/AuthProvider'
import { useEffect, useState } from 'react'
import { getPain, removePain, updatePain } from '../service/AxiosService'
import ItemGallery from '../components/ItemGallery'

export default function PainDiary() {
  const { token } = useAuth()
  const [painList, setPainList] = useState([])

  useEffect(() => {
    getPain(token).then(data => setPainList(data.painList))
  }, [token])

  const reloadPainList = token => {
    getPain(token).then(data => setPainList(data.painList))
  }

  return (
    <Wrapper>
      <Header title="Pain Diary" />
      <Content>
        <ul>
          {
            <ItemGallery
              List={painList}
              valueTitle={'Pain'}
              reloadList={reloadPainList}
              editService={updatePain}
              removeService={removePain}
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

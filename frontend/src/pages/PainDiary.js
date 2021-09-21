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
  const [painMap, setPainMap] = useState([])

  useEffect(() => {
    getPain(token).then(data => setPainMap(data.painMap))
  }, [token])

  const reloadPainList = token => {
    getPain(token).then(data => setPainMap(data.painMap))
  }

  return (
    <Wrapper>
      <Header title="Pain Diary" />
      <Content>
        <ul>
          {
            <ItemGallery
              map={painMap}
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

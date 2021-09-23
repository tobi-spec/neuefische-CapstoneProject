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
  const [painMaps, setPainMaps] = useState([])

  useEffect(() => {
    getPain(token)
        .then(data => setPainMaps(data.painMaps))
  }, [token])

  const reloadPainList = token => {
    getPain(token)
        .then(data => setPainMaps(data.painMaps))
  }

  return (
    <Wrapper>
      <Header title="Pain Diary" />
            <ItemGallery
                itemMaps={painMaps}
                valueTitle={'Pain'}
                reloadList={reloadPainList}
                editService={updatePain}
                removeService={removePain}
            />
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
`

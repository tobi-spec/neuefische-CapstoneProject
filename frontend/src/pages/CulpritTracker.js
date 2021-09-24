import styled from "styled-components";
import Header from "../components/Header";
import Footer from "../components/Footer";
import {useEffect, useState} from "react";
import {useAuth} from "../auth/AuthProvider";
import {getPain} from "../service/AxiosService";

export default function CulpritTracker(){
    const {token} = useAuth()
    const [painMaps, setPainMaps] = useState([])

    useEffect(() => {
        getPain(token).then(data => setPainMaps(data.painMaps))
    }, [token])

    const xValues = painMaps.map(map => map.date)
    const yValues = painMaps.map(map => map.pains)

    console.log(xValues)
    console.log(yValues)

    return <Wrapper>
        <Header title="Culprit Tracker"/>
        <Footer/>
    </Wrapper>
}

const Wrapper = styled.div``
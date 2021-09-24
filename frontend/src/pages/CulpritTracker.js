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
        getPain(token)
            .then(data => setPainMaps(data.painMaps))
    }, [token])


    const xValues = () => painMaps.map(map => map.date)

    const yValues = () => {
        const pains = painMaps.map(map => map.pains)

        const yValueArray = []
        let previousDate = "";
        let dailyPainLevel = 0;
        let valuesEachDay = 0

        pains.forEach(list =>
            list.map(painDto =>
            {
                if(painDto.date !== previousDate){
                    let avr = dailyPainLevel / valuesEachDay
                    if(avr > 0){
                        yValueArray.push(avr)
                        dailyPainLevel = 0
                        valuesEachDay = 0
                    }
                }
                previousDate = painDto.date
                dailyPainLevel += painDto.painLevel
                valuesEachDay += 1
            }
            ))
        return yValueArray
    }

    console.log(xValues())
    console.log(yValues())

    return <Wrapper>
        <Header title="Culprit Tracker"/>
        <Footer/>
    </Wrapper>
}

const Wrapper = styled.div``
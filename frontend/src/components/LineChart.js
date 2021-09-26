import React, {useEffect, useState} from "react";
import {Line} from 'react-chartjs-2'
import {useAuth} from "../auth/AuthProvider";
import {getFood, removeFood, updateFood} from "../service/AxiosService";
import ItemGallery from "./ItemGallery";

export default function LineChart({yValues, xValues}){
    const {token} = useAuth()
    const [date, setDate] = useState("")
    const [foodMaps, setFoodMaps] = useState([])

    useEffect(() => {
        getFood(token)
            .then(data => setFoodMaps(data.foodMaps))
    }, [token])

    const reloadFoodList = token => {
        getFood(token)
            .then(data => setFoodMaps(data.foodMaps))
    }

    const getElementAtEvent = element => {
        if (!element.length) return;

        const { index } = element[0];
        setDate(
            `${data.labels[index]}`
        );
    };

    const filterData = foodMaps.filter(map => map.date === date)

    const xAxes = xValues()
    const yAxes = yValues()

    const  data={
                labels: xAxes,
                    datasets: [
                        {
                            label: "trace of the perpetrators",
                            data: yAxes,
                            backgroundColor: ['rgba(11, 156, 49, 0.2)'],
                            borderColor: ['rgba(11, 156, 49, 0.2)'],
                            borderWidth: 5,
                        }
                    ],
                }

    const LineChart = () => {
        return (
            <div>
            <div>
            <Line data={data} height={100} width={150} getElementAtEvent={getElementAtEvent}/>
            </div>
                <ItemGallery
                    itemMaps={filterData}
                    valueTitle={"Food"}
                    reloadList={reloadFoodList}
                    editService={updateFood}
                    removeService={removeFood}
                />
            </div>)
    }

    return LineChart()
}



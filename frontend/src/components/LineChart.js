import React, {useState} from "react";
import {Line} from 'react-chartjs-2'

export default function LineChart({yValues, xValues}){
    const [clickedElement, setClickedElement] = useState("")

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

    const options={}

    const getElementAtEvent = element => {
        if (!element.length) return;

        const { datasetIndex, index } = element[0];
        setClickedElement(
            `${data.labels[index]} - ${data.datasets[datasetIndex].data[index]}`
        );
    };

    const LineChart = () => {
        return (<div>
            <Line data={data} options={options} height={100} width={150} getElementAtEvent={getElementAtEvent}/>
            <p>{clickedElement}</p>
            </div>
    )
    }

    return LineChart()
}



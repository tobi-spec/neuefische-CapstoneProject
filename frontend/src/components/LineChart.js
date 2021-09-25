import React from "react";
import {Line} from 'react-chartjs-2'
import styled from "styled-components/macro";

export default function LineChart({yValues, xValues}){

    const xAxes = xValues()
    const yAxes = yValues()

const LineChart = () => {
    return (
        <Wrapper>
            <Line
                data={{
                    labels: xAxes,
                    datasets: [{
                        label: "trace of the perpetrators",
                        data: yAxes,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1,
                    }]
                }}
                height={200}
                width={300}
            />
        </Wrapper>
    )
}

return LineChart()
}

const Wrapper = styled.div`

`


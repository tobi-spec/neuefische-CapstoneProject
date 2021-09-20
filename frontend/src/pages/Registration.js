import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import InputField from "../components/InputField";

export default function Registration() {
    return <Wrapper>
            <Header title={Registration}/>
            <Content>
                <InputField title="Username"/>
                <InputField title="Password"/>
            </Content>
        </Wrapper>

}

const Wrapper = styled.div``


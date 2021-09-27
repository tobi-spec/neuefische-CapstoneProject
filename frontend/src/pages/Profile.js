import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";

export default function Profile(){

    return (<Wrapper>
        <Header title="Welcome"/>
        <Content>
            <p>change password</p>
            <p>delete data</p>
            <p>delete account</p>
        </Content>
    </Wrapper>)
}

const Wrapper = styled.div``
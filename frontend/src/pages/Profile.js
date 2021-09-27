import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import Footer from "../components/Footer";

export default function Profile(){

    return (<Wrapper>
        <Header title="Profile"/>
        <Content>
            <p>change password</p>
            <p>delete data</p>
            <p>delete account</p>
        </Content>
        <Footer/>
    </Wrapper>)
}

const Wrapper = styled.div``
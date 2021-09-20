import styled from "styled-components";
import Content from "../components/Content";
import Button from "../components/Button";

export default function Welcome() {
    return<Wrapper>
        <Content>
            <h1>Intestinal Inspector</h1>
            <h2>Welcome</h2>
            <Button title=Log-in/>
            <Button title=Sign-in/>
        </Content>
    </Wrapper>
}

const Wrapper = styled.div`
`

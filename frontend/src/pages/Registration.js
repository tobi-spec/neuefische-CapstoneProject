import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import InputField from "../components/InputField";
import {useState} from "react";
import Button from "../components/Button";
import {createUser} from "../service/AxiosService";

export default function Registration() {
    const [credentials, setCredentials] = useState({
        userName: "",
        userPassword: "",
    })

    const credentialsHandler  = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value});
    }

    const signSubmitHandler = event => {
        event.preventDefault()
        createUser(credentials).catch(error => console.error(error))
    }

    return <Wrapper onSubmit={signSubmitHandler}>
            <Header title="Registration"/>
            <Content>
                <InputField
                    title="Username"
                    type="Text"
                    value={credentials.userName}
                    onChange={credentialsHandler }/>
                <InputField
                    title="Password"
                    type="Text"
                    value={credentials.userPassword}
                    onChange={credentialsHandler }/>
                <Button>sign-in</Button>
            </Content>
        </Wrapper>

}

const Wrapper = styled.form``


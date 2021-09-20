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
        createUser(credentials)
            .then(response => console.log(response))
            .catch(error => console.error(error))
            .finally(() => setCredentials({
                userName: "",
                userPassword: "",
            }))

    }

    return <Wrapper onSubmit={signSubmitHandler}>
            <Header title="Registration"/>
            <Content>
                <div className = "input">
                <InputField
                    title="Username"
                    type="Text"
                    name="userName"
                    value={credentials.userName}
                    onChange={credentialsHandler }/>
                <InputField
                    title="Password"
                    type="Text"
                    name="userPassword"
                    value={credentials.userPassword}
                    onChange={credentialsHandler }/>
                </div>
                <Button>sign</Button>

            </Content>
        </Wrapper>

}

const Wrapper = styled.form`
  
  Button {
    grid-column: 2;
    grid-row: 3;
  }
  
  .input {
    grid-column: 2;
    grid-row: 2;
  }
`


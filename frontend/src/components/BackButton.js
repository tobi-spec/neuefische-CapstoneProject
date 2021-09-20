import {useHistory} from "react-router-dom";
import Button from "./Button";

export default function BackButton(){
    const history = useHistory()
    return <Button title="Back" onClick={history.goBack()}/>
}
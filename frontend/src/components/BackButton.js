import {useHistory} from "react-router-dom";
import Button from "./Button";

export default function BackButton(){
    const history = useHistory()
    return <Button onClick={history.goBack}> Back </Button>
}
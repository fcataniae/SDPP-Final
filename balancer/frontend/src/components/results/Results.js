import React from "react";
import './Results.css';
import {DocumentResult} from "./documents/DocumentResult";
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Container from "@material-ui/core/Container";
import SearchText from "../searchtext/SearchText";


export default class Results extends React.Component {

    render = () => {
        let show = this.props.location.state.results.length !== 0;
        let toRender = show ? <RenderResults results={this.props.location.state.results}/> : null;
        return (
            <Container fixed className='p-top-2'>
                <SearchText fix="left"></SearchText>
                {toRender}
            </Container>
            );
    }
}

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    documentResult: {
        padding: '0.65rem!important',
        display: 'inline',
    },
}));


export const RenderResults = ({results}) => {

    const classes = useStyles();


    return (

        <Grid container className={classes.root} spacing={1}>
                {results.map((element,index) => (
                    <Grid item xs key={index} className={classes.documentResult} >
                            <DocumentResult  result={element}/>
                    </Grid>
                ))}
        </Grid>
    )
};
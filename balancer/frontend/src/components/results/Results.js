import React from "react";
import './Results.css';
import {DocumentResult} from "./documents/DocumentResult";
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';


export default class Results extends React.Component {

    constructor(props){
        super(props);
        this.state = { data : [] };
        if(this.props.location.state){
            this.state.data = this.props.location.state.results;
            console.log(this.state.data);
        }
    }
    render = () => {
        return (
            <RenderResults results={this.state.data}/>
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
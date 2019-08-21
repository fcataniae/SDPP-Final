import React from "react";

export default class Results extends React.Component {

    constructor(props){
        super(props);
        if(this.props.location.state)
            console.log(this.props.location.state.results);
    }
    render(){
        return (
            <div >
Results
            </div>
        );
    }
}
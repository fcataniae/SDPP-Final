import React from "react";

export default class Results extends React.Component {

    constructor(props){
        super(props);
        this.state = { data : [] };
        if(this.props.location.state)
            this.state.data = this.props.location.state.results;
    }
    render(){
        return (<div>
            { this.state.data.map(function(item) {
                return <div>{item}</div>
            })
            }
        </div>);
    }
}
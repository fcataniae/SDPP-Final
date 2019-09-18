import React from "react";
import './Results.css';
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

        let elements = this.state.data.map( (element) => {
           let type;

           switch (element.type) {
               case 'PDF':
                   type = <div className="rounded mx-auto d-block pdf-miniature"></div> ;
                   break;

               case 'TXT':
                   type = <div className=" rounded mx-auto d-block txt-miniature"></div> ;
                   break;
               case 'DOCX':
                   type = <div className="rounded mx-auto d-block docx-miniature"></div> ;
                   break;
           }
            return  <div>{type}
                <div>{element.name}</div>
                <div>{element.node}</div>
                    </div>

        });

        return <div>{elements}</div>

    }
}


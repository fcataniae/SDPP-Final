import {
  createReducer,
  on
} from '@ngrx/store';
import {ConfigurationComponent} from "../components/configuration/configuration.component";
import * as Action from "./app.actions";


export interface State {
  enlaces : any[];
  brand : any;
  configuration: any;
  version: string;
}

const initialState: State = {
  enlaces: [{view: 'Subir archivos', url: '/uploads'},
            {view: 'Compartidos', url: '/shareds'},
            {view: 'Busqueda avanzada', url: '/searchs'}],
  brand:  {url: '/', logoUrl: './assets/logo.jpg', logo: 'SD&PP'},
  configuration:{component: ConfigurationComponent},
  version: ''
};


export const _menuReducer = createReducer(initialState,
  on(Action.getVersion, state => ({...state, version: state.version})),
  on(Action.succesVersion, (state, action)=> ({...state, version: action.version})),
  on(Action.errorVersion, (state, action) => ({...state, version: action.version}))
);




export function menuReducer(state, action) {
  return _menuReducer(state, action);
}

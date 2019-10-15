import {createAction, props} from '@ngrx/store';

export const getVersion = createAction(
  '[Get Version]'
);

export const successGetVersion = createAction(
  '[Success get Version]',
        props<{version: string}>()

);

export const errorGetVersion = createAction(
  '[Error get Version]',
  props<{error: any}>()
)


export const getAllFiles = createAction(
  '[Get Files]'
)

export const successGetAllFiles = createAction(
  '[Success get all Files]',
  props<{files: any[]}>()
)

export const errorGetAllFiles = createAction(
  '[Error get all Files]',
  props<{error: any}>()
)

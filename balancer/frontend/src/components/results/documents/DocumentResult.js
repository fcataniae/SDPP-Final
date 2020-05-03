import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red } from '@material-ui/core/colors';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import GetAppIcon from '@material-ui/icons/GetApp';

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: 'auto',
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },
    avatar: {
        backgroundColor: red[500],
    },
}));

export const DocumentResult = ({result}) => {

    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const handleExpandClick = () => {
        setExpanded(!expanded);
    };
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
    const creationDate = new Date(result.meta.createdTime).toLocaleDateString('en-US', options);
    const modificationTime = new Date(result.meta.modifiedTime).toLocaleDateString('en-US', options);
    return (
        <Card className={classes.root}>
            <CardHeader
                avatar={
                    <Avatar aria-label='recipe' className={classes.avatar}>
                        {result.meta.extension.toUpperCase()}
                    </Avatar>
                }
                title={result.name}
                subheader={`Fecha ${creationDate}`}
            />
            <CardActions disableSpacing>
                <a href={result.link} download target='_blank' rel='noopener noreferrer'>
                    <IconButton
                        aria-label='download'
                    >
                        <GetAppIcon />
                    </IconButton>
                </a>
                <IconButton
                    className={clsx(classes.expand, {
                        [classes.expandOpen]: expanded,
                    })}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label='show more'
                >
                    <ExpandMoreIcon />
                </IconButton>
            </CardActions>
            <Collapse in={expanded} timeout='auto' unmountOnExit>
                <CardContent>
                    <Typography paragraph>Modificacion: {modificationTime} </Typography>
                    <Typography paragraph>Autor: {result.meta.author} </Typography>
                    <Typography paragraph>Tamaño: {result.meta.size / 1024 } KB </Typography>
                </CardContent>
            </Collapse>
        </Card>
    );
}

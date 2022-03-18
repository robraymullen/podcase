import { SubscribedPodcast } from "../../Types";
import ReactHtmlParser from 'react-html-parser';
import React from "react";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";
import Box from '@mui/material/Box';
import {useNavigate} from "react-router-dom";

const PodcastGridItem = (podcast: SubscribedPodcast) => {

    const navigate = useNavigate();

    return (
        <Grid item xs={3}>
            <Card sx={{ minWidth: 250, minHeight: 400 }} onClick={() => {
                navigate(`/podcast/${podcast.id}`)
                }
            }>
                <CardContent>
                    <Typography variant="h5" component="div" align="center">
                        {podcast.name}
                    </Typography>
                    <Box
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                    >
                    <img src={podcast.imageUrl} width="50%" height="50%"/>
                    </Box>
                    <Typography sx={{ mb: 1.5, fontSize: 10 }} color="text.secondary" variant="subtitle1">
                        {ReactHtmlParser(podcast.description)}
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small">Learn More</Button>
                </CardActions>
            </Card>
        </Grid>

    )
}

export default PodcastGridItem;

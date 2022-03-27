import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';
import { getUserSubscriptions, getAllPodcasts } from '../../services/PodcaseAPIService';
import { GridRoutes, Podcast, SubscribedPodcast } from '../../Types';
import PodcastGridItem from '../PodcastGridItem/PodcastGridItem';
import {useLocation} from "react-router-dom";

const PodcastGrid = (props: any) => {

    const location = useLocation();

    useEffect(() => {
        if (props && props.state === GridRoutes.PODCAST_ALL) {
            getAllPodcasts(props.setPodcasts, () => {});
        } else if (props && props.state === GridRoutes.PODCAST_SUBSCRIPTION) {
            getUserSubscriptions(1, props.setPodcasts, () => {});
        }
        
    }, [location]);

    return (
            <Grid container spacing={2} key="podcastGrid">
            {
                props.podcasts.map((podcast: Podcast) => {
                    return <PodcastGridItem key={podcast.id} id={podcast.id} description={podcast.description} imageUrl={podcast.imageUrl} name={podcast.name}></PodcastGridItem>
                })
            }
            </Grid>
    )
}

export default PodcastGrid;

import React, { useState, useEffect, useRef } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { SubscribedEpisode } from '../../Types';
import Typography from '@mui/material/Typography';

interface PlaybarProps {
    currentEpisode: SubscribedEpisode | undefined;
}

const Playbar = ({ currentEpisode }: PlaybarProps) => {

    const audioRef = useRef<HTMLAudioElement>(null);

    useEffect(() => {
        if (currentEpisode && audioRef && audioRef.current) {
            audioRef!.current!.pause();
            audioRef!.current!.load();
            audioRef.current.currentTime = currentEpisode.play_length;
        }
    }, [currentEpisode]);

    return (
        <Box
            sx={{
                position: 'fixed',
                bottom: "0",
                width: "100%",
                height: "150px",
                backgroundColor: "rgb(50, 80, 240)",
            }}>
            <Grid container spacing={2}
                display="flex"
                alignItems="center"
                justifyContent="center"

                sx={{
                    paddingRight: "15%",
                    paddingTop: "50px",
                }}
            >
                <Grid item
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                    sx={{
                        width: 1,
                    }}>
                    {
                        currentEpisode &&
                        <Box
                            sx={{
                                paddingLeft:"10%",
                                width: 1,
                            }}
                        >
                            <Grid item xs={12} sm container
                                sx={{
                                    width: 1,
                                }}
                            >
                                <Grid item
                                    sx={{
                                        width: 1,
                                    }}>
                                    <Typography variant="h6" noWrap sx={{color:"white", paddingBottom: "5px"}}>
                                        {currentEpisode.title}
                                    </Typography>
                                </Grid>
                                <Grid item
                                    sx={{
                                        width: 1,
                                    }}>
                                    <audio controls ref={audioRef} className="audioPlayer">
                                        {
                                            currentEpisode.downloaded &&
                                            <source src={`http://localhost:7070/podcast/audio/${currentEpisode.file_name}`} />
                                        }
                                        <source src={currentEpisode.file_url} type="audio/mp3" />

                                This browser does not support web audio.
                            </audio>
                                </Grid>
                            </Grid>
                        </Box>



                    }

                </Grid>
            </Grid>
        </Box>
    );
};

export default Playbar;
import { useEffect, useRef } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { SubscribedEpisode, PlayState } from '../../Types';
import Typography from '@mui/material/Typography';
import { AppContext } from '../../context/context';
import { useContext, useState } from 'react';
import { updateLastPlayed } from '../../services/PodcaseAPIService';

const Playbar = () => {

    const audioRef = useRef<HTMLAudioElement>(null);
    const { state, dispatch } = useContext(AppContext);

    let intervalId: any;

    const startUpdates = () => {
        if (!intervalId) {
            intervalId = setInterval(sendUpdates, 5000);
        }
    };

    const stopUpdates = () => {
        clearInterval(intervalId);
        intervalId = null;
    };

    const sendUpdates = () => {
        if (state.currentUser && state.currentEpisode && audioRef.current) {
            state.currentEpisode.play_length = Math.floor(audioRef.current.currentTime);
            updateLastPlayed(state.currentUser.id, state.currentEpisode, (playState: PlayState) => {
            });
        }
    };

    useEffect(() => {
        if (state.currentEpisode && audioRef && audioRef.current) {
            audioRef!.current!.pause();
            audioRef!.current!.load();
            audioRef.current.currentTime = state.currentEpisode.play_length;
        }
    }, [state.currentEpisode]);

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
                        state.currentEpisode &&
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
                                        {state.currentEpisode.title}
                                    </Typography>
                                </Grid>
                                <Grid item
                                    sx={{
                                        width: 1,
                                    }}>
                                    <audio controls ref={audioRef} className="audioPlayer" onPlaying={startUpdates} onPause={stopUpdates}>
                                        {
                                            state.currentEpisode.downloaded &&
                                            <source src={`http://localhost:7070/podcast/audio/${state.currentEpisode.file_name}`} />
                                        }
                                        <source src={state.currentEpisode.file_url} type="audio/mp3" />

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
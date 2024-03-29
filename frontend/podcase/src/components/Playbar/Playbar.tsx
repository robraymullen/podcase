import { useEffect, useRef } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { SubscribedEpisode, PlayState } from '../../Types';
import Typography from '@mui/material/Typography';
import { AppContext } from '../../context/context';
import { useContext, useState } from 'react';
import { getNextEpisode, updateLastPlayed } from '../../services/PodcaseAPIService';
import { changeEpisode, setAutoPlay, changePlayMessage } from '../../context/reducer';
import { ResponseError } from '../../Error/ResponseError';
import FastRewindIcon from '@mui/icons-material/FastRewind';
import IconButton from '@mui/material/IconButton';
import FastForwardIcon from '@mui/icons-material/FastForward';

const Playbar = () => {

    const audioRef = useRef<HTMLAudioElement>(null);
    const { state, dispatch } = useContext(AppContext);

    const [intervalId, setIntervalId] = useState<any | null>(null);

    const startUpdates = () => {
        if (!intervalId) {
            setIntervalId(setInterval(sendUpdates, 5000));
        }
    };

    const stopUpdates = () => {
        clearInterval(intervalId);
        setIntervalId(null);
    };

    const sendUpdates = () => {
        if (state.currentUser && state.currentEpisode && audioRef.current) {
            state.currentEpisode.play_length = Math.floor(audioRef.current.currentTime);
            updateLastPlayed(state.currentUser.id, state.currentEpisode, (playState: PlayState) => {
            });
        }
    };

    const nextEpisode = () => {
        if (state.currentUser && state.currentEpisode && audioRef.current) {
            stopUpdates();
            getNextEpisode(state.currentEpisode.id, state.currentUser.id, (nextEpisode: SubscribedEpisode) => {
                dispatch(setAutoPlay(true));
                dispatch(changeEpisode(nextEpisode));
            }, (error: ResponseError) => {
                if (error.getResponse().status === 404) {
                    dispatch(changePlayMessage({
                        text: `No next episode available for podcast!`,
                        severity: "warning",
                        visible: true,
                    }));
                } else {
                    dispatch(changePlayMessage({
                        text: "Error fetching next episode!",
                        severity: "error",
                        visible: true,
                    }))
                }
            });
        }
    };

    const rewind = () => {
        audioRef!.current!.currentTime = audioRef!.current!.currentTime - 10;
    };

    const fastForward = () => {
        audioRef!.current!.currentTime = audioRef!.current!.currentTime + 10;
    };

    useEffect(() => {
        if (state.currentEpisode && audioRef && audioRef.current) {
            stopUpdates();
            audioRef!.current!.pause();
            audioRef!.current!.load();
            audioRef.current.currentTime = state.currentEpisode.play_length;
            if (state.autoPlay) {
                audioRef.current.play();
            }
        }
    }, [state.currentEpisode, state.autoPlay]);

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
                                paddingLeft: "10%",
                                width: 1,
                            }}
                        >
                            <Grid item xs={12} sm container
                                sx={{
                                    width: 1,
                                }}
                            >
                                <Grid item
                                    display="flex"
                                    alignItems="center"
                                    justifyContent="center"
                                    sx={{
                                        width: 1,
                                    }}>
                                    <IconButton sx={{ color: "white", marginRight: "50px" }} onClick={rewind}>
                                        <FastRewindIcon />
                                    </IconButton>
                                    <Typography variant="h6" noWrap sx={{ color: "white", paddingBottom: "5px" }}>
                                        {state.currentEpisode.title}
                                    </Typography>
                                    <IconButton sx={{ color: "white", marginLeft: "50px" }} onClick={fastForward}>
                                        <FastForwardIcon />
                                    </IconButton>
                                </Grid>
                                <Grid item
                                    display="flex"
                                    alignItems="center"
                                    justifyContent="center"
                                    sx={{
                                        width: 1,
                                    }}>
                                    <audio controls ref={audioRef} className="audioPlayer"
                                        onPlaying={startUpdates}
                                        onPause={stopUpdates}
                                        onEnded={nextEpisode}
                                    >
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
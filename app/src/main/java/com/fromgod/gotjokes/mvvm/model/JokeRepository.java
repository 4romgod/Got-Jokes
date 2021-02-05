package com.fromgod.gotjokes.mvvm.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JokeRepository {

    private JokeDAO jokeDao;
    private LiveData<List<Joke>> jokeListLiveData;

    public JokeRepository(Application application) {
        JokeDB jokeDB = JokeDB.getInstance(application);
        jokeDao = jokeDB.jokeDAO();
        jokeListLiveData = jokeDao.getAllJokes();
    }

    public LiveData<List<Joke>> getAllJokes(){
        return jokeListLiveData;
    }

    public  LiveData<Joke> getJoke(int id){
        return jokeDao.getJoke(id);
    }

    public LiveData<Integer> getCount(int id){
        return jokeDao.getCount(id);
    }

    public void insert(Joke joke){
        new InsertAsyncTask(jokeDao).execute(joke);
    }

    public void update(Joke joke){
        new UpdateAsyncTask(jokeDao).execute(joke);
    }

    public void delete(Joke joke){
        new DeleteAsyncTask(jokeDao).execute(joke);
    }

    public void deleteAllItems(){
        new DeleteAllAsyncTask(jokeDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Joke, Void, Void> {
        private JokeDAO jokeDao;

        public InsertAsyncTask(JokeDAO jokeDao) {
            this.jokeDao = jokeDao;
        }

        @Override
        protected Void doInBackground(final Joke... params) {
            jokeDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Joke, Void, Void> {
        private JokeDAO jokeDao;

        public UpdateAsyncTask(JokeDAO jokeDao) {
            this.jokeDao = jokeDao;
        }

        @Override
        protected Void doInBackground(final Joke... params) {
            jokeDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Joke, Void, Void>{
        private JokeDAO jokeDao;

        public DeleteAsyncTask(JokeDAO jokeDao) {
            this.jokeDao = jokeDao;
        }

        @Override
        protected Void doInBackground(final Joke... params) {
            jokeDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private JokeDAO jokeDao;

        public DeleteAllAsyncTask(JokeDAO jokeDao) {
            this.jokeDao = jokeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jokeDao.deleteAllJokes();
            return null;
        }
    }

}

package com.fromgod.got_jokes.mvvm.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JokeRepository {

    private JokeDAO jokeDAO;
    private LiveData<List<Joke>> mAllJokes;


    public JokeRepository(Application application){
        JokeDB db = JokeDB.getINSTANCE(application);
        jokeDAO = db.jokeDAO();
        mAllJokes = jokeDAO.getAllJokes();
    }       //end constructor()

    public LiveData<List<Joke>> getAllJokes(){
        return mAllJokes;
    }       //end getAllJokes()


    public void insert(Joke joke){
        new InsertAsyncTask(jokeDAO).execute(joke);
    }       //end insert()


    public void update(Joke joke){
        new UpdateAsyncTask(jokeDAO).execute(joke);
    }       //end update()


    public void delete(Joke joke){
        new DeleteAsyncTask(jokeDAO).execute(joke);
    }       //end delete()


    public void deleteAllItems(){
        new DeleteAllAsyncTask(jokeDAO).execute();
    }       //end deleteAllItems()



    //------------------------------------------------------ASYNC OPERATIONS----------------------------
    private static class InsertAsyncTask extends AsyncTask<Joke, Void, Void> {
        private JokeDAO mAsyncTaskDao;

        public InsertAsyncTask(JokeDAO mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Joke... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }       //end innerClass


    private static class UpdateAsyncTask extends AsyncTask<Joke, Void, Void>{
        private JokeDAO mAsyncTaskDao;

        public UpdateAsyncTask(JokeDAO mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Joke... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }       //end innerClass


    private static class DeleteAsyncTask extends AsyncTask<Joke, Void, Void>{
        private JokeDAO mAsyncTaskDao;

        public DeleteAsyncTask(JokeDAO mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Joke... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }       //end innerClass


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private JokeDAO mAsyncTaskDao;

        public DeleteAllAsyncTask(JokeDAO mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllJokes();
            return null;
        }
    }       //end innerClass

}
